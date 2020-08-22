import React from 'react';

class PasswordInput extends React.Component {
    constructor (props) {
        super(props);
        this.state = {
            value : ''
        };

        this.handleChange = this.handleChange.bind(this);
    }

    handleChange (event) {
        this.setState({
            value : event.target.value
        });
    }

    render () {
        return (
            <input type="password" value={this.state.value} onChange={this.handleChange} />
        );
    }
}

export default PasswordInput;
