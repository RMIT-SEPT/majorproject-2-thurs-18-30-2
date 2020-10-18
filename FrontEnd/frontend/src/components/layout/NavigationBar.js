import React from 'react';
import { Navbar, Nav, NavDropdown, Image } from 'react-bootstrap';
import { withRouter } from 'react-router-dom';
import { connect } from 'react-redux';
import { compose } from 'redux';

import { logOut } from '../../app/reducers/userSlice';
import profilePic from '../../images/dwight1.jpg';
import '../../css/NavBar.css';

class NavigationBar extends React.Component {
    render () {
        return (
            <Navbar className="navigation-bar" bg="dark" variant="dark">
                <Navbar.Brand href="#home">AGME</Navbar.Brand>

                <Nav className="mr-auto">
                    <Nav.Link href="#contact-us">Contact Us</Nav.Link>
                    <Nav.Link href="#about-us">About Us</Nav.Link>
                </Nav>

                <Nav className="justify-content-end">
                    
                </Nav>
                
                <NavDropdown 
                    title={this.props.user.userDetails ?
                        <span className="fa-stack fa-lg">
                            <Image src={profilePic} className="nav-profile-pic fa-stack-1x" roundedCircle fluid />
                        </span>
                        :  
                        <span className="fa-stack">
                            <i className="fa fa-user-circle fa-2x"></i>
                        </span> 
                    }
                    id="nav-dropdown" 
                    className="mr-sm-2"
                >
                    {this.props.user.userDetails 
                        ?
                        <React.Fragment>
                            <NavDropdown.Item href="#profile">
                                <span className="fa-stack fa-lg">
                                    
                                    <Image src={profilePic} className="nav-profile-pic fa-stack-1x" roundedCircle fluid />
                                </span>
                                
                                Profile
                            </NavDropdown.Item>
                            <NavDropdown.Divider />
                            <NavDropdown.Item href="#log-in" onClick={() => this.props.logOut()}>
                                <span className="fa-stack fa-lg">
                                    <i className="fa fa-circle fa-stack-2x fa-inverse"></i>
                                    <i className="fa fa-sign-out-alt fa-stack-1x"></i>
                                </span>
                                Log Out
                            </NavDropdown.Item>
                        </React.Fragment>
                        :
                        <React.Fragment>
                            <NavDropdown.Item href="#sign-up">
                                <span className="fa-stack fa-lg">
                                    <i className="fa fa-circle fa-stack-2x fa-inverse"></i>
                                    <i className="fa fa-user-plus fa-stack-1x"></i>
                                </span> 

                                Sign Up     
                            </NavDropdown.Item>
                            <NavDropdown.Divider />
                            <NavDropdown.Item href="#log-in">
                                <span className="fa-stack fa-lg">
                                    <i className="fa fa-circle fa-stack-2x fa-inverse"></i>
                                    <i className="fa fa-sign-in-alt fa-stack-1x"></i>
                                </span>

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