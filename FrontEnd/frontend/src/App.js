import React from 'react';
import { Switch, Redirect } from 'react-router-dom';
import { connect } from 'react-redux';
import { Container, Row, Col } from 'react-bootstrap';

import './css/App.css';

import SubRouter from './components/utils/SubRouter';
import NavigationBar from './components/layout/NavigationBar';
import SideBar from './components/layout/SideBar';
import Modal from './components/utils/Modal';
import router from './router/router';
import { } from './app/reducers/userSlice';
import SideBarMenu from './side-bar-menus/side-bar-menus';

class App extends React.Component {
    render () {

        var items = []
        var sideBarMenu = new SideBarMenu();
        if(this.props.user.userDetails) {
            if(this.props.user.userDetails.empType) {

            } else {
                items = sideBarMenu.customerMenu();
            }
        } else {
            console.log("guest")
            items = sideBarMenu.guestMenu();
        }
        return (
            <React.Fragment>
                <Modal />
                <Container className="container">
                    <Row className="custom-row">
                        <NavigationBar />
                    </Row>
                    <Row className="custom-row row-full-height">
                        <Col className="custom-col" xs="4" md="3">
                            <SideBar items={items} />
                        </Col>
                        <Col xs="14" md="9">
                            <Switch>
                                {router.map((route, i) => (
                                        <SubRouter key={i} {...route} />
                                    )
                                )}
                                <Redirect to="/home" />
                            </Switch>
                        </Col>
                    </Row>
                    
                </Container>
            </React.Fragment>
        );
    }
}

const mapStateToProps = state => ({
    user : state.user,
    modal : state.modal
});

const mapDispatchToProps = () => {
    return {
        
    };
};

export default connect(
    mapStateToProps,
    mapDispatchToProps()
)(App);
