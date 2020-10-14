import React from 'react';
import { Switch } from 'react-router-dom';
import { Nav } from 'react-bootstrap';
import { connect } from 'react-redux';
import SubRouter from './utils/SubRouter';

import '../css/BookingPage.css';

class BookingPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            routes : this.props.router.routes,
            timeState : 'present'
        }
        this.selectHandler = this.selectHandler.bind(this);
    }

    selectHandler(eventKey) {
        if(eventKey === '#/bookings/present') {
            this.setState({
                timeState : 'present'
            })
        } else {
            this.setState({
                timeState : 'past'
            })
        }
    }

    render() {
        var html;
        if(this.props.user.userDetails) {
            var apiCall;
            if(this.props.user.userDetails.empType) {
                if(this.props.user.userDetails.empType === 'admin') {
                    apiCall = '/booking/getAllBookings';
                } else {
                    apiCall = '/booking/getBookings/employee/' + this.props.user.userDetails.id;
                }
            } else {
                apiCall = '/booking/getBookings/customer/' + this.props.user.userDetails.id;
            }
            var routes = this.state.routes;
            routes[0].listApi = apiCall + '/' + this.state.timeState;
            routes[1].listApi = apiCall + '/' + this.state.timeState;
            
            html = (
                <React.Fragment>
                    <Nav justify variant="tabs" onSelect={this.selectHandler} className="tabBar">
                        {
                            routes.map((route) => {
                                return (
                                    <Nav.Item key={route.path}>
                                        <Nav.Link href={'#' + route.path}>{route.label}</Nav.Link>
                                    </Nav.Item>
                                );
                            })
                        }    
                    </Nav>

                    <Switch>
                        {this.state.routes.map((route, i) => (
                                <SubRouter key={i} {...route} />
                            )
                        )}
                        
                    </Switch>
                </React.Fragment>
            );
        } else {
            html = <React.Fragment>Loading...</React.Fragment>
        }
        return html;
    }
}

const mapStateToProps = state => ({
    user : state.user
});

const mapDispatchToProps = () => {
    return { 
    };
};

export default connect(
    mapStateToProps,
    mapDispatchToProps()
)(BookingPage);