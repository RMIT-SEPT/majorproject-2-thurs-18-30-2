import React from 'react';
import { Switch, Redirect } from 'react-router-dom';
import { connect } from 'react-redux';
import { Container, Row, Col } from 'react-bootstrap';
import jwt_decode from 'jwt-decode';
import './css/App.css';

import api from './app/api';
import SubRouter from './components/utils/SubRouter';
import NavigationBar from './components/layout/NavigationBar';
import SideBar from './components/layout/SideBar';
import Modal from './components/utils/Modal';
import router from './router/router';
import { setUser, setDecoded } from './app/reducers/userSlice';
import SideBarMenu from './side-bar-menus/side-bar-menus';

class App extends React.Component {

    async componentDidMount() {
        if(!this.props.user.userDecoded) {       
            var token = localStorage.getItem('jwtToken');  
            if(token === 'null') {
                token = null
            }
            if(token !== null) {  
                this.props.setDecoded(token);
                var decoded = jwt_decode(token);
                try {
                    var { data } = await api.get('/user/getUser/' + decoded.id);
                    this.props.setUser(data);
                } catch(error) {
                    console.log(error.repsonse);
                }
            }
        } 
    }

    render () {
        var items = []
        var sideBarMenu = new SideBarMenu();
        if(this.props.user.userDetails) {
            if(this.props.user.userDetails.empType) {
                if(this.props.user.userDetails.empType === 'admin') {
                    items = sideBarMenu.ownerMenu();
                } else {
                    items = sideBarMenu.employeeMenu();
                }
            } else {
                items = sideBarMenu.customerMenu();
            }
        } else {
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
        setUser,
        setDecoded
    };
};

export default connect(
    mapStateToProps,
    mapDispatchToProps()
)(App);
