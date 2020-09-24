import React from 'react';
import { Row, Col } from 'react-bootstrap';
import api from '../app/api';
import EmployeeCard from './EmployeeCard';

class EmployeesPage extends React.Component {
    constructor (props) {
        super(props);
        this.state = {
            employees : []
        };
    }

    async componentDidMount() {
        try {
            const response = await api.get('/employee/getAllEmployees');
            this.setState({
                employees : response.data
            });
        } catch(error) {
            console.log(error.response);
        }
    }
    
    render () {
        
        var html;
        
        var rowList = [];
        var tmpList = [];
        var i = 0;
        var j = 0;
        for(j = 0; j !== this.state.employees.length; ++j) {
            tmpList.push(this.state.employees[j])
            i++;
            if(i === 4 || j + 1 === this.state.employees.length) {
                
                rowList.push(
                    <Row key={j}>
                        {tmpList.map((employeeCol) => {
                            return ( 
                                <Col key={employeeCol.id}>
                                    <EmployeeCard employee={employeeCol}/>
                                </Col>
                            );
                        })}
                    </Row>
                );
                i = 0;
                tmpList = [];
            }
        }
        
        html = (
            <React.Fragment>
                    {this.state.employees &&    
                        rowList.map((row) => {
                            return row;
                        })
                    }
                   
            </React.Fragment>
        )
        return html;
    }

}

export default EmployeesPage;