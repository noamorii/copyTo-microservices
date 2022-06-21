import React, {useEffect, useState} from "react";
import axios from "axios";
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

    // async function fetchOrders() {
    //     // const fetchOrders = await fetch('http://localhost:8081/api/v1/orders');
    //     // const response = await fetchOrders.json();
    //     const fetchOrders = await fetch('http://localhost:8081/api/v1/orders')
    //         .then(response => setOrders(response.json()));
    //     setOrders(response);
    //     console.log(orders)
    // }

    // async getAllOrders() {
    //     const url = 'http://localhost:8081/api/v1/orders';
    //     const response = await fetch(url);
    //     const orders = await response.json();
    //     return orders;
    // }

    return(
        <div>
            <Table>
                <thead></thead>
                <tbody>
                {orders.map(orders => {
                    return(
                        <tr>
                            <td>{orders.id}</td>
                            {/*<td>{orders.insertionDate}</td>*/}
                            {/*<td>{orders.assigneeId}</td>*/}
                            <td>{orders.clientId}</td>
                        </tr>
                    )
                })}
                </tbody>
            </Table>
        </div>
    )
}