import React from 'react';
import EmployeeCard from './EmployeeCard';
import { mount } from 'enzyme';
import { HashRouter as Router } from 'react-router-dom';
import { Provider } from 'react-redux';
import store from '../app/store';
import Enzyme from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';

Enzyme.configure({ adapter: new Adapter() });
describe('<EmployeeCard /> component Unit Test', () => {
    const props = {
        item : {
            fName : 'Chinguun',
            lName : 'Undrakh',
            username : 'MyUsername',
            id : 1,
            email : 'chinguun9903@gmail.com',
            createdAt : '2020-03-20'
        }
    }
    it('should render <EmployeeCard /> component', () => {
        const component = mount(<Provider store={store}>
                                    <Router>
                                        <EmployeeCard {...props}/>
                                    </Router>
                                </Provider>);
        
        const titleText = component.find('#title').map((node) => node.text())[0];
        expect(titleText).toMatch('MyUsername');
        const footerText = component.find('.text-muted').map((node) => node.text())[0];
        expect(footerText).toMatch('Created At : 2020-03-20');
    });
})