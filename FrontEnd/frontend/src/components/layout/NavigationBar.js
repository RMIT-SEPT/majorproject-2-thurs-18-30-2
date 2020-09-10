import React from 'react';
import { Navbar, Nav, NavDropdown} from 'react-bootstrap';
import { withRouter } from 'react-router-dom';
import { connect } from 'react-redux';
import { compose } from 'redux';

import { logOut } from '../../app/reducers/userSlice';

class NavigationBar extends React.Component {
    render () {
        return (
            <Navbar bg="dark" variant="dark">
                <Navbar.Brand href="#home">AGME</Navbar.Brand>

                <Nav className="mr-auto">
                    <Nav.Link href="#contact-us">Contact Us</Nav.Link>
                    <Nav.Link href="#about-us">About Us</Nav.Link>
                </Nav>

                <Nav className="justify-content-end">
                    
                </Nav>
                
                <NavDropdown 
                    title={this.props.user.loggedIn ? this.props.user.userDetails.fName : 
                        (this.props.location.pathname === "/log-in" ? "Sign Up" : "Log In")} 
                    id="collasible-nav-dropdown" 
                    className="mr-sm-2"
                >
                    {this.props.user.loggedIn 
                        ?
                        <React.Fragment>
                            <NavDropdown.Item href="#home">
                                Profile
                            </NavDropdown.Item>
                            <NavDropdown.Divider />
                            <NavDropdown.Item href="#log-in" onClick={() => this.props.logOut()}>
                                Log Out
                            </NavDropdown.Item>
                        </React.Fragment>
                        :
                        <React.Fragment>
                            <NavDropdown.Item href="#sign-up">
                                Sign Up
                            </NavDropdown.Item>
                            <NavDropdown.Divider />
                            <NavDropdown.Item href="#log-in">
                                Log In
                            </NavDropdown.Item>
                        </React.Fragment>    
                    }
                </NavDropdown>
            </Navbar>
        );
    }

}

const mapStateToProps = state => ({
    user : state.user
});

const mapDispatchToProps = () => {
    return {
        logOut
    };
};

export default compose(
    connect(mapStateToProps, mapDispatchToProps()),
    withRouter
)(NavigationBar);