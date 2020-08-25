import React from 'react';
import { Switch } from 'react-router-dom';

import './css/App.css';
import SubRouter from './components/SubRouter';
import router from './router/router';

class App extends React.Component {
  render () {
    return (
          <Switch>
            {router.map((route, i) => (
                    <SubRouter key={i} {...route} />
                )
            )}
          </Switch>
    );
  }
}

export default App;
