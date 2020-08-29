import React from 'react';
import { Form, Row, Col } from 'react-bootstrap';
import _ from 'lodash';


class PasswordInputWithConf extends React.Component {
    constructor (props) {
        super(props);
        this.state = {
            value : '',
            value1 : '',
            valid : false
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
            if(this.value == this.value1) {
                this.setState({
                    valid : true
                });
            }
            else {
                this.setState({
                    valid : false
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
                    <Form.Control placeholder="Password" type="password" value={this.state.value} onChange={this.handleChange} />
                    </Col>
                </Form.Group>
                
                <Form.Group as={Row}>
                    <Form.Label column sm={this.props.pos[0]}>
                        Confirm {this.props.naming}
                    </Form.Label>
                    <Col sm={this.props.pos[1]}>
                    <Form.Control placeholder="Confirm Password" type="password" value={this.state.value1} onChange={this.handleChangeConf} />
                    </Col>
                </Form.Group>
            </Form>
        );
    }
}

export default PasswordInputWithConf;