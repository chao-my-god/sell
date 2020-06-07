package com.chao.sell.service.impl;

import com.chao.sell.converter.OrderMaster2OrderDTO;
import com.chao.sell.dataobject.OrderDetail;
import com.chao.sell.dataobject.OrderMaster;
import com.chao.sell.dataobject.ProductInfo;
import com.chao.sell.dto.CartDTO;
import com.chao.sell.dto.OrderDTO;
import com.chao.sell.enums.OrderStatusEnum;
import com.chao.sell.enums.PayStatusEnum;
import com.chao.sell.enums.ResultEnum;
import com.chao.sell.exception.SellException;
import com.chao.sell.repository.OrderDetailRepository;
import com.chao.sell.repository.OrderMasterRepository;
import com.chao.sell.repository.ProductInfoRepository;
import com.chao.sell.service.OrderService;
import com.chao.sell.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductServiceImpl productService;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        //生成唯一键值
        String orderId = KeyUtil.getUniqueKey();
        //订单总价
        BigDecimal order_total = new BigDecimal(BigInteger.ZERO);
        //下单步骤
        //1、查询商品数量和单价
        //用户选择商品和数量后，由前端提交数据并写到orderDTO
        List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();
        List<OrderDetail> orderDetailList1 = new ArrayList<>();
        for(OrderDetail orderDetail:orderDetailList){
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if(productInfo==null){
                //throw抛出的是异常类的实例对象
                //该异常类继承RuntimeException,为的是异常抛出时能够执行事务回滚
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2、计算总价
            order_total = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(order_total);

            //3、订单详情入库
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailList1.add(orderDetail);
        }
        //4、订单主表入库
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.UNPAID.getCode());
        orderMaster.setOrderTotal(order_total);
        orderMasterRepository.save(orderMaster);
        //订单详情入库
        for(OrderDetail orderDetail:orderDetailList1){
            orderDetailRepository.save(orderDetail);
        }
        //5、减库存
        //java8 lambda
        //购物车(商品ID+数量)
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.decrease(cartDTOList);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO findOne(String orderId) {
        OrderDTO orderDTO = new OrderDTO();
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).get();
        if(orderMaster==null)
            throw new SellException(OrderStatusEnum.ORDER_NOT_EXIST);
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList))
            throw new SellException(OrderStatusEnum.ORDERDETAIL_NOT_EXIST);
        orderDTO.setOrderDetailList(orderDetailList);
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String customerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByCustomerOpenid(customerOpenid, pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTO.convert(orderMasterPage.getContent());
        //使用PageImpl将list转为Page
        return new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode()))
            throw new SellException(OrderStatusEnum.ORDER_STATUS_ERROR);

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult==null)
            throw new SellException(OrderStatusEnum.ORDER_UPDATE_FAIL);

        //不用删除相关订单详情记录，因为这样用户才可以查询历史订单
        //只需修改状态
        //修改库存
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList()))
            throw new SellException(OrderStatusEnum.ORDERDETAIL_NOT_EXIST);
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e->new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increase(cartDTOList);

        //已支付就执行退款

        return orderDTO;
    }

    @Override
    @Transactional
    //OrderDTO由前端提交数据获得
    public OrderDTO finish(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode()))
            throw new SellException(OrderStatusEnum.ORDER_STATUS_ERROR);
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult==null)
            throw new SellException(OrderStatusEnum.ORDER_UPDATE_FAIL);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode()))
            throw new SellException(OrderStatusEnum.ORDER_STATUS_ERROR);
        //修改订单状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.UNPAID.getCode()))
            throw new SellException(OrderStatusEnum.ORDER_PAY_STATUS_ERROR);
        orderDTO.setPayStatus(PayStatusEnum.PAID.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult==null)
            throw new SellException(OrderStatusEnum.ORDER_UPDATE_FAIL);
        return orderDTO;
    }
}
