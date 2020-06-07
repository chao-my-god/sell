package com.chao.sell.controller;

import com.chao.sell.VO.ResultVO;
import com.chao.sell.converter.OrderForm2OrderDTO;
import com.chao.sell.dataobject.OrderMaster;
import com.chao.sell.dto.OrderDTO;
import com.chao.sell.enums.OrderStatusEnum;
import com.chao.sell.enums.ResultEnum;
import com.chao.sell.exception.SellException;
import com.chao.sell.form.OrderForm;
import com.chao.sell.service.impl.CustomerServiceImpl;
import com.chao.sell.service.impl.OrderServiceImpl;
import com.chao.sell.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class CustomerOrderController {

    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private CustomerServiceImpl customerService;

    //创建订单
    //@Valid和BindingResult结合用于表单验证
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm
                                                    ,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确,orderForm={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode()
                    ,bindingResult.getFieldError().getDefaultMessage());
        }
        //数据转换，便于数据入库
        OrderDTO orderDTO = OrderForm2OrderDTO.convert(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】购物车为空");
            throw new SellException(OrderStatusEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO createResult = orderService.create(orderDTO);
        Map<String,String> map = new HashMap<>();
        map.put("orderId",createResult.getOrderId());
        return ResultVOUtil.success(map);
    }
    //订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid
            ,@RequestParam("page") Integer page, @RequestParam("size") Integer size){
        if(StringUtils.isEmpty(openid))
            throw new SellException(ResultEnum.PARAM_ERROR);
        PageRequest request = PageRequest.of(page,size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid,request);
        return ResultVOUtil.success(orderDTOPage.getContent());
    }

    //订单详情
    @GetMapping("/detail")
    public ResultVO detail(@RequestParam("openid") String openid
            ,@RequestParam("orderId") String orderId){
        //查询订单
        OrderDTO orderDTO = customerService.findOneOrder(openid,orderId);
        return ResultVOUtil.success(orderDTO);
    }

    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid
            ,@RequestParam("orderId") String orderId){
        OrderDTO orderDTO = customerService.cancelOrder(openid,orderId);
        return ResultVOUtil.success();
    }


}
