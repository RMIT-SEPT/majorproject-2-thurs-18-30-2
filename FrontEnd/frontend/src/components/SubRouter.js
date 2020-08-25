import React from 'react';
import { Route } from 'react-router-dom';
import { Jumbotron, Container } from 'react-bootstrap';
import '../css/log-in.css';

export default function SubRouter(route) {
    return (
        <div className={route.style}>
            <Route
                path={route.path}
                render={props => (
                    // pass the sub-routes down to keep nesting
                    <route.component {...props} router={route} />
                )}
            />
        </div>
    );
};