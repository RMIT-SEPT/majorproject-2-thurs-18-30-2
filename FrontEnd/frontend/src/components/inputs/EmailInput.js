import React from 'react';
import _ from 'lodash';

class EmailInput extends React.Component {
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

        event.persist();
        if (this.debounceFn) {
            this.debounceFn.cancel()
            this.debounceFn = false
          }
        this.debounceFn = _.debounce(() => {
            let searchString = event.target.value;
            // Here should be API call to check if email already exists.
            console.log(searchString);
        }, 700, { leading : false, trailing: true });

        this.debounceFn();
    }

    render () {
        return (
            <input type="text" value={this.state.value} onChange={this.handleChange} />
        );
    }
}

export default EmailInput;