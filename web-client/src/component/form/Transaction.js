import React, {Component} from 'react';
import {Button, Icon, Input, Row} from "react-materialize";


class Transaction extends Component {
    constructor(props) {
        super(props);
        this.state = {
            amount: '',
            type: ''
        }
    }

    handleChange = (e) => {
        const {target: {name, value}} = e;
        this.setState({[name]: value});
    };

    handleSubmit = (e) => {
        e.preventDefault();
        const {type, amount} = this.state;

        fetch('http://localhost:8080/transaction', {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            method: 'POST',
            body: JSON.stringify({type, amount}),
        })

    };

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <Row>
                    <Input name='amount' s={12} label="Amount" onChange={this.handleChange}/>
                    <Input name='type' s={12} type='select' label="Type" onChange={this.handleChange}>
                        <option value='DEBIT'>Debit</option>
                        <option value='CREDIT'>Credit</option>
                    </Input>
                    <Button waves='light' className='orange' type='submit'>Make Transaction
                        <Icon left>send</Icon>
                    </Button>
                </Row>
            </form>
        );
    }
}

export default Transaction;