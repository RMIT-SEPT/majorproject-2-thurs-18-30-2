import React from 'react';
import { Form } from 'react-bootstrap';
import api from '../app/api';
import EmployeeCard from './EmployeeCard';

class EmployeesPage extends React.Component {
    constructor (props) {
        super(props);
        this.state = {
            employees : null
        };
    }

    async componentDidMount() {
        try {
            const response = await api.get('/employee/getAllEmployees');
            await this.setState({
                employees : response.data
            });
        } catch(error) {
            console.log(error.response);
        }
    }
    
    render () {
        
        var html;
        
        html = (
            <Form.Group>

                <div className="Margin">
                    <div className="row">

                    {this.state.employees &&    
                        this.state.employees.map(
                            function(e) {
                                return  (
                                    
                                    <EmployeeCard key={e.id} employee={e}/>
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