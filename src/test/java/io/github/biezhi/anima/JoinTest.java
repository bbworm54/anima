package io.github.biezhi.anima;

import io.github.biezhi.anima.core.Joins;
import io.github.biezhi.anima.model.Address;
import io.github.biezhi.anima.model.OrderInfo;
import io.github.biezhi.anima.model.User;
import io.github.biezhi.anima.model.UserDto;
import org.junit.Assert;
import org.junit.Test;

import static io.github.biezhi.anima.Anima.select;

/**
 * Join test
 *
 * @author biezhi
 * @date 2018/4/16
 */
public class JoinTest extends BaseTest {

    @Test
    public void testJoin() {
        // HasOne
        OrderInfo orderInfo = select().from(OrderInfo.class)
                .join(
                        Joins.with(Address.class).as(OrderInfo::getAddress)
                                .on(OrderInfo::getId, Address::getOrderId)
                )
                .byId(3);

        Assert.assertNotNull(orderInfo);
        Assert.assertNotNull(orderInfo.getAddress());

        orderInfo = select().from(OrderInfo.class)
                .join(
                        Joins.with(Address.class).as(OrderInfo::getAddress)
                                .on(OrderInfo::getId, Address::getOrderId)
                )
                .join(
                        Joins.with(User.class).as(OrderInfo::getUser)
                                .on(OrderInfo::getUid, User::getId)
                )
                .byId(3);

        Assert.assertNotNull(orderInfo);
        Assert.assertNotNull(orderInfo.getAddress());
        Assert.assertNotNull(orderInfo.getUser());

        // ManyToOne
        orderInfo = select().from(OrderInfo.class)
                .join(
                        Joins.with(User.class).as(OrderInfo::getUser)
                                .on(OrderInfo::getUid, User::getId)
                )
                .byId(3);

        Assert.assertNotNull(orderInfo);
        Assert.assertNotNull(orderInfo.getUser());

        // OneToMany
        UserDto userDto = select().from(UserDto.class).join(
                Joins.with(OrderInfo.class).as(UserDto::getOrders)
                        .on(UserDto::getId, OrderInfo::getUid)
        ).byId(1);
        Assert.assertNotNull(userDto);
        Assert.assertNotNull(userDto.getOrders());
    }

}
