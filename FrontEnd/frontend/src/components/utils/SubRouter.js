import React from 'react';
import { Route } from 'react-router-dom';

import '../../css/centered.css';

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