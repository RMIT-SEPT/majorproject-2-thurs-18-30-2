import React from 'react';
import { Form, Row, Col } from 'react-bootstrap';
import _ from 'lodash';

class UsernameInput extends React.Component {
    constructor (props) {
        super(props);
        this.state = {
            value : '',
            valid : false,
            border : "1px solid black",
            errMsg : ""
        };

        this.handleChange = this.handleChange.bind(this);
    }

    

    handleChange (event) {
        this.setState({
            value : event.target.value
        });

        

        event.persist();

        if (this.debounceFn) {
            this.debounceFn.cancel()
            this.debounceFn = false
        }

        this.debounceFn = _.debounce(() => {
            //insert api call

            if(!this.valid) {
                this.setState({
                    border : '1px solid red',
                    errMsg : "Username already exists, please try a different username"
                });
            }
        }, 700, { leading : false, trailing: true });

        this.debounceFn();
    }

    render () {
        return (

            
            <Form.Group as={Row}>
                <Form.Label column sm={this.props.pos[0]}>
                    {this.props.naming}
                </Form.Label>
                <Col sm={this.props.pos[1]}>
                    <Form.Control style={{border: this.state.border}} placeholder="Username" type="Username" value={this.state.value} onChange={this.handleChange} />
                    <p3 style={{color: 'red', fontSize: '12px'}}>
                        {this.state.errMsg}
                    </p3>
                </Col>
            </Form.Group>
        );
    }
}

export default UsernameInput;
