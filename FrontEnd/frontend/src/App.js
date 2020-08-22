import React from 'react';
import Form from './components/Form'

class App extends React.Component {
  render () {
    var form = {
      components : [
        {
          inputName : 'Name',
          inputType : 'text'
        },
        {
          inputName : 'Email',
          inputType : 'email'
        },
        {
          inputName : 'Password',
          inputType : 'password'
        }
      ]
    };
    return (
      <Form form={form}/>
    );
  }
}

export default App;
