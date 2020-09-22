import React from 'react';
import { Form } from 'react-bootstrap';
import Booking from '../components/Booking';
import '../css/BookingPage.css';

class BookingsPage extends React.Component {
    constructor (props) {
        super(props);
        this.state = {
        };
    }

    // async componentDidMount() {
    //     try {
    //         const response = await api.get('/employee/getAllEmployees');
    //         await this.setState({
    //             employees : response.data
    //         });
    //     } catch(error) {
    //         console.log(error.response);
    //     }
    // }

    //A contact us page
    render () {
        
        var html;
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
                },
                {
                    eFName : "employeeFirstName",
                    eLName : "employeeLastName",
                    date : "14/09/2020",
                    startTime : "1930",
                    endTime : "2030",
                    service : "Dine and Dash"
                },
                {
                    eFName : "employeeFirstName",
                    eLName : "employeeLastName",
                    date : "14/09/2020",
                    startTime : "1930",
                    endTime : "2030",
                    service : "Dine and Dash"
                },
                {
                    eFName : "employeeFirstName",
                    eLName : "employeeLastName",
                    date : "14/09/2020",
                    startTime : "1930",
                    endTime : "2030",
                    service : "Dine and Dash"
                },
                {
                    eFName : "employeeFirstName",
                    eLName : "employeeLastName",
                    date : "14/09/2020",
                    startTime : "1930",
                    endTime : "2030",
                    service : "Dine and Dash"
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

                <div class="Margin">
                <div className="row">

                {       
                    call.map(
                        function(book) {
                            return  (
                                
                                    <Booking 
                                        booking={book}
                                    />
                            );
                        }
                    ) 
                }
                </div>
                </div>
            </Form.Group>
        )
        return html;
    }

}

export default BookingsPage;