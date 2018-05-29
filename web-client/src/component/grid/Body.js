import React, {Component} from 'react';
import {Col, Row} from "react-materialize";
import Transaction from "../form/Transaction";
import History from "../table/History";

class Body extends Component {
    constructor(props) {
        super(props);
        this.state = {
            historyUpdated: false,
        }
    }

    onHistoryUpdate = () => {
        console.log("onHistoryUpdate");
    };

    render() {
        return <Row className='container center'>
            <Col s={12}>
                    <h3 className="header orange-text base">Let's operate your money</h3>
                <h6 className="light">You can perform 'debit' and 'credit' transactions by using this pretty
                    simple tool. </h6>
            </Col>
            <Col className='offset-s1' s={3}>
                <Transaction updateHistory={this.onHistoryUpdate}/>
            </Col>
            <Col className='offset-s1' s={7}>
                <History/>
            </Col>
        </Row>;
    }
}

export default Body