import React from 'react';
//import './css/AboutUs.css';

class AboutUs extends React.Component {
    constructor (props) {
        super(props);
        this.state = {

        };
    }

    //About us page
    render () {

        return (
            <React.Fragment>
            <h2><u>About Us</u></h2>
            <h4>At AGME, we believe in providing customers the best services.</h4>
            <br></br>
            <p>AGME was created with the ambition of providing customers satisfactory services by booking such service.
                The company strives to provide fast and excellent customer services.
                AGME ensure the services for the customers are reliable and efficient.</p>
            </React.Fragment>
        );
    }

}

export default AboutUs;