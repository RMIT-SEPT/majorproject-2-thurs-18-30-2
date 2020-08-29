import React from 'react';
import { Navbar, Nav, NavDropdown} from 'react-bootstrap';

class NavigationBar extends React.Component {
    constructor (props) {
        super(props);
        this.state = {

        };
    }

    render () {
        return (
            <Navbar bg="dark" variant="dark">
                <Navbar.Brand href="#/home">AGME</Navbar.Brand>

                <Nav className="mr-auto">
                    <Nav.Link href="#/home">Contact Us</Nav.Link>
                    <Nav.Link href="#/home">About Us</Nav.Link>
                </Nav>

                <Nav className="justify-content-end">
                    <Nav.Link href="#/log-in">Log in</Nav.Link>
                    <Nav.Link href="#/sign-up">Sign Up</Nav.Link>
                </Nav>

                <NavDropdown title="Username" id="collasible-nav-dropdown" className="mr-sm-2">
                    <NavDropdown.Item href="#/home">Profile</NavDropdown.Item>
                    <NavDropdown.Divider />
                    <NavDropdown.Item href="#/home">Sign Out</NavDropdown.Item>
                </NavDropdown>
            </Navbar>
        );
    }

}

export default NavigationBar;