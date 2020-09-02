import React from 'react';
import { Form, Row, Col } from 'react-bootstrap';
import _ from 'lodash';


class PasswordInputWithConf extends React.Component {
    constructor (props) {
        super(props);
        this.state = {
            value : '',
            valueConf : '',
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
            valueConf : event.target.value
        });

        // here
        event.persist();

        if (this.debounceFn) {
            this.debounceFn.cancel()
            this.debounceFn = false
        }

        this.debounceFn = _.debounce(() => {
            if(this.value === this.valueConf) {
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
            <React.Fragment>
                <Form.Group as={Row}>
                    <Form.Label column sm={this.props.pos[0]}>
                        {this.props.naming}
                    </Form.Label>
                    <Col sm={this.props.pos[1]}>
                    <Form.Control placeholder={this.props.naming} type="password" value={this.state.value} onChange={this.handleChange} />
                    </Col>
                </Form.Group>
                    
                <Form.Group as={Row}>
                    <Form.Label column sm={this.props.pos[0]}>
                        Confirm {this.props.naming}
                    </Form.Label>
                    <Col sm={this.props.pos[1]}>
                    <Form.Control placeholder={'Confirm' + this.props.naming} type="password" value={this.state.valueConf} onChange={this.handleChangeConf} />
                    </Col>
                </Form.Group>
            </React.Fragment>
        );
    }
}

export default PasswordInputWithConf;