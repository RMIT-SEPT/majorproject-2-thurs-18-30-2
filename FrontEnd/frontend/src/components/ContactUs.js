import React from 'react';
//import './css/ContactUs.css';

class ContactUs extends React.Component {
    constructor (props) {
        super(props);
        this.state = {

        };
    }
    //A contact us page
    render () {

        return (

            <React.Fragment>
                <h2><b>Contact Us</b></h2>

                <h3>Phone</h3>
                <p>0412 345 678</p>

                <h3>Email</h3>
                <p>AGME@agmesupport.com</p>

                <h3>Address:</h3>
                <p>Melbourne CBD, Victoria, Australia</p>

                <h3>Socials</h3>
                <a href="www.twitter.com">Twitter</a>
                <br></br>
                <a href="www.facebook.com">Facebook</a>
                <br></br>
                <a href="www.instagram.com">Instagram</a>
            
            </React.Fragment>
        );
    }

}

export default ContactUs;