import React from 'react';
import { Switch, Redirect } from 'react-router-dom';
import { connect } from 'react-redux';

import './css/App.css';

import SubRouter from './components/utils/SubRouter';
import NavigationBar from './components/layout/NavigationBar';
import Modal from './components/utils/Modal';
import router from './router/router';
import { } from './app/reducers/userSlice';

class App extends React.Component {
  render () {
    return (
        <React.Fragment>
            <NavigationBar />
            <Modal />
            <Switch>
                {router.map((route, i) => (
                        <SubRouter key={i} {...route} />
                    )
                )}
                <Redirect to="/home" />
            </Switch>
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
