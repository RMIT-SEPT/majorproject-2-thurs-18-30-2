import React from 'react';
import CardsListPage from './CardsListPage';
import EmployeeCard from './EmployeeCard';
import { mount } from 'enzyme';
import { HashRouter as Router } from 'react-router-dom';
import { Provider } from 'react-redux';
import store from '../app/store';
import Enzyme from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import api from '../app/api';
import mockAxios from 'axios';

Enzyme.configure({ adapter: new Adapter() });
describe('<CardsListPage /> component Unit Test', () => {
    jest.mock('axios')
    const props = {
        listApi : "/employee/getAllEmployees",
        card : EmployeeCard
    }
    var items = [
        {
            fName : 'Chinguun',
            lName : 'Undrakh',
            username : 'MyUsername',
            id : 1,
            email : 'chinguun9903@gmail.com',
            createdAt : '2020-03-20'
        }
    ]
    // mockAxios.get.mockResolvedValue({
    //     data : items
    // })
    
    it('should render <CardsListPage /> component', async () => {
        
        const component = mount(<Provider store={store}>
                                    <Router>
                                        <CardsListPage router={props}/>
                                    </Router>
                                </Provider>);
        // mocking axios
    });
})