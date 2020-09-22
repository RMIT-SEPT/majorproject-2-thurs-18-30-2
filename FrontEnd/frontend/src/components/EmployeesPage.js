import React from 'react';
import { Form } from 'react-bootstrap';
import EmployeeCard from './EmployeeCard';

class EmployeesPage extends React.Component {
    constructor (props) {
        super(props);
        this.state = {
        };
    }
    
    render () {
        
        var html;
        var call = [
            {
                username : 'testUsername',
                fName : 'first name',
                lName : 'last name',
                email : 'testemail@email.com'
            },
            {
                username : 'testUsername',
                fName : 'first name',
                lName : 'last name',
                email : 'testemail@email.com'
            },
            {
                username : 'testUsername',
                fName : 'first name',
                lName : 'last name',
                email : 'testemail@email.com'
            },
            {
                username : 'testUsername',
                fName : 'first name',
                lName : 'last name',
                email : 'testemail@email.com'
            }
        ]
        html = (
            <Form.Group>

                <div class="Margin">
                <div className="row">

                {       
                    call.map(
                        function() {
                            return  (
                                
                                    <EmployeeCard
                                    username='testUsername'
                                    fName='first name'
                                    lName='last name'
                                    email='testemail@email.com'/>
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

export default EmployeesPage;