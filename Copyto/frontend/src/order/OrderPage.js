import React, {useEffect, useState} from "react";
import {Table} from "react-bootstrap";

export const OrderPage = () => {
    const [orders, setOrders] = useState([]);

    useEffect( () => {
        async function fetchOrders() {
            // const fetchOrders = await fetch(
            //     'http://localhost:8081/api/v1/orders'
            // ).then(response => response.json()).catch(err => console.log(err));
            const fetchOrders = await fetch('http://localhost:8081/api/v1/orders');
            const response = await fetchOrders.json();
            setOrders(response);
            console.log(orders);
        }
        fetchOrders()
        }, [])


    return(
        <div>
            <Table style={{color:"white"}}>
                <thead></thead>
                <tbody>
                {orders.map(orders => {
                    return(
                        <tr>
                            <td key={orders.id}>{orders.id}</td>
                            <td key={orders.insertionDate}>{orders.insertionDate}</td>
                            {/*<td key={orders.assigneeId}>{orders.assigneeId}</td>*/}
                            {/*<td key={orders.clientId}>{orders.clientId}</td>*/}
                        </tr>
                    )
                })}
                </tbody>
            </Table>
        </div>
    )
}