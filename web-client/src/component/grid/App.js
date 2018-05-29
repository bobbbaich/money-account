import React from 'react';
import {Col, Row} from "react-materialize";
import Header from "./Header";
import Body from "./Body";
import Footer from "./Footer";


export default () => {
    return (
        <Row>
            <Col s={12}>
                <Header/>
            </Col>
            <Col s={12}>
                <Body/>
            </Col>
            <Col s={12}>
                <Footer/>
            </Col>
        </Row>
    );
}