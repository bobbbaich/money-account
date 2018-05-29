import React, {Component} from 'react';
import {Table} from "react-materialize";

class History extends Component {
    constructor(props) {
        super(props);
        this.state = {
            rows: []
        }
    }

    componentWillUnmount() {
        this.unmounted = true;
    }

    componentDidMount() {
        fetch("http://localhost:8080/transaction/history")
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error('Something went wrong ...');
                }
            })
            .then(transactions => {
                let rows = transactions.map(
                    t => {
                        return <tr key={t.id}
                                   className={" " + (t.type === 'DEBIT' ? 'green-text' : 'red-text')}>
                            <td>{t.id}</td>
                            <td>{t.type}</td>
                            <td>{t.amount}</td>
                            <td>{t.total}</td>
                            <td>{t.dateTime}</td>
                        </tr>
                    }
                );
                !this.unmounted && this.setState({rows: rows});

            })
            .catch(error => !this.unmounted && this.setState({error}));
    }

    render() {
        return <Table>
            <thead>
            <tr>
                <th data-field="id">Id</th>
                <th data-field="type">Type</th>
                <th data-field="amount">Amount</th>
                <th data-field="total">Total</th>
                <th data-field="dateTime">Date Time</th>
            </tr>
            </thead>
            <tbody>
            {this.state.rows}
            </tbody>
        </Table>
    };
}

export default History