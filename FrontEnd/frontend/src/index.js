import React from 'react';
import ReactDOM from 'react-dom';
import { HashRouter as Router } from 'react-router-dom';
import { Provider } from 'react-redux';

import 'bootstrap/dist/css/bootstrap.min.css';
import '@fortawesome/fontawesome-free/css/all.min.css';
import './css/index.css';

import App from './App';

import * as serviceWorker from './serviceWorker';
import store from './app/store';
import setJWTToken from './app/securityUtils/setJWTToken';

var token = localStorage.getItem('jwtToken');  
if(token === 'null') {
    token = null
}
if(token !== null) {
    setJWTToken(token);
}


ReactDOM.render(
    <React.StrictMode>
        <Provider store={store}>
            <Router>
                <App />
            </Router>
        </Provider>
    </React.StrictMode>,
    document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
