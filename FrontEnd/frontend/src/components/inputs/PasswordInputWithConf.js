import React from 'react';
import { Form, Row, Col } from 'react-bootstrap';
import _ from 'lodash';


class PasswordInputWithConf extends React.Component {
    constructor (props) {
        super(props);
        this.state = {
            value : '',
            value1 : '',
            valid : false,
            border : "1px solid black",
            errMsg : ""
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleChangeConf = this.handleChangeConf.bind(this);
    }

    handleChange (event) {
        this.setState({
            value : event.target.value
        });
    }

    handleChangeConf (event) {
        this.setState({
            value1 : event.target.value
        });

        // here
        event.persist();

        if (this.debounceFn) {
            this.debounceFn.cancel()
            this.debounceFn = false
        }

        this.debounceFn = _.debounce(() => {
            if(this.state.value == this.state.value1 && this.state.value.length >= 8) {
                this.setState({
                    valid : true
                });
            }
            else if(this.state.value != this.state.value1) {
                this.setState({
                    valid : false,
                    border : '1px solid red',
                    errMsg : "Passwords do not match, please check and try again"
                });
            }
            else {
                this.setState({
                    valid : false,
                    border : '1px solid red',
                    errMsg : "Password is too short, please try a longer password"
                });
            }
            if(this.state.valid) {
                this.setState({
                    border : '1px solid green',
                    errMsg : ""
                });
            }
        }, 700, { leading : false, trailing: true });

        this.debounceFn();
    }

    render () {
        return (
            <Form>
                <Form.Group as={Row}>
                    <Form.Label column sm={this.props.pos[0]}>
                        {this.props.naming}
                    </Form.Label>
                    <Col sm={this.props.pos[1]}>
                    <Form.Control style={{border: this.state.border}} placeholder="Password" type="password" value={this.state.value} onChange={this.handleChange} />
                    </Col>
                </Form.Group>
                
                <Form.Group as={Row}>
                    <Form.Label column sm={this.props.pos[0]}>
                        Confirm {this.props.naming}
                    </Form.Label>
                    <Col sm={this.props.pos[1]}>
                    <Form.Control style={{border: this.state.border}} placeholder="Confirm Password" type="password" value={this.state.value1} onChange={this.handleChangeConf} />
                    <p style={{color: 'red', fontSize: '12px'}}>
                        {this.state.errMsg}
                    </p>
                    </Col>
                </Form.Group>
            </Form>
        );
    }
}

export default PasswordInputWithConf;