import React from 'react';
import { Form, Row, Col } from 'react-bootstrap';
import Booking from '../components/Booking';
import '../css/Profile.css';

class BookingsPage extends React.Component {
    constructor (props) {
        super(props);
        this.state = {
        };
    }
    //A contact us page
    render () {
        
        var html;
        var c = 0;
        var r = 0;
        var call = [
                {
                    eFName : "efirst",
                    eLName : "elast",
                    date : "15/09/2020",
                    startTime : "0930",
                    endTime : "1030",
                    service : "Full Body Massage"
                },
                {
                    eFName : "another1",
                    eLName : "another2",
                    date : "15/09/2020",
                    startTime : "1330",
                    endTime : "1530",
                    service : "Super Happy Fun Times"
                },
                {
                    eFName : "employeeFirstName",
                    eLName : "employeeLastName",
                    date : "14/09/2020",
                    startTime : "1930",
                    endTime : "2030",
                    service : "Dine and Dash"
                }
            ]
        
        html = (
            <Form.Group>
                {       
                    call.map(
                        function(book) {
                            return  (
                                    <Booking 
                                        booking={book}
                                    />
                            );
                        }.bind(this)
                    ) 
                }
            </Form.Group>
        )
        return html;
    }

}

export default BookingsPage;