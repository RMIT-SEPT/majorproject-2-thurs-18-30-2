import React from 'react';
import { Form, Row, Col } from 'react-bootstrap';
import _ from 'lodash';
import { connect } from 'react-redux';
import { compose } from 'redux';
import { withRouter } from 'react-router-dom';
import api from '../../app/api';

class UsernameInput extends React.Component {
    constructor (props) {
        super(props);
        this.state = {
            value : '',
            valid : true,
            border : "1px solid lightgrey",
            errMsg : "",
            changed : false
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
            
            api.get(`/user/usernameExists/${this.state.value}`)
            .then((response) => {
                var exists = response.data;
                if(!exists) {
                    this.setState({
                        valid : true
                    });
                } else {
                    this.setState({
                        valid : false
                    });
                }

                if(this.state.valid) {
                    this.setState({
                        border : '1px solid lightgreyn',
                        errMsg : ""
                    });
                }
                else {
                    this.setState({
                        border : '1px solid red',
                        errMsg : "Username already exists, please try a different username"
                    });
                }
            })
            .catch((error) => {
                console.log(error);
            });
        }, 700, { leading : false, trailing: true });

        this.debounceFn();
    }

    render () {
        if(this.props.user.userDetails) {
            if(!this.state.changed) {
                this.setState({
                    value : this.props.user.userDetails.username,
                    changed : true
                });
            }
        }
        return (
            <Form.Group as={Row}>
                <Form.Label column sm={this.props.pos[0]}>
                    {this.props.naming}
                </Form.Label>
                <Col sm={this.props.pos[1]}>
                    <Form.Control style={{border: this.state.border}} placeholder="Username" type="Username" value={this.state.value} onChange={this.handleChange} />
                    <p style={{color: 'red', fontSize: '12px'}}>
                        {this.state.errMsg}
                    </p>
                </Col>
            </Form.Group>
        );
        
    }
}

const mapStateToProps = state => ({
    user : state.user
});

const mapDispatchToProps = () => {
    return {
    };
};

export default compose(
    connect(mapStateToProps, mapDispatchToProps()),
    withRouter
)(UsernameInput);
