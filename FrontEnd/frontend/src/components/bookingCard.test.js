import React from 'react';
import BookingCard from './BookingCard';
import { mount } from 'enzyme';
import { Provider } from 'react-redux';
import store from '../app/store';
import Enzyme from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';

Enzyme.configure({ adapter: new Adapter() });
describe('<BookingCard /> component Unit Test', () => {
    const mockFn = jest.fn();
    const props = {
        item : {
            employeeFName : 'Chinguun',
            employeeLName : 'Undrakh',
            bServiceDescription : 'Cool Service dexc',
            bServieName : 'Coolest Service',
            employeeEmail : 'chinguun9903@gmail.com',
            customerFName : 'Anthony',
            customerLName : 'Ojaimi',
            scheduleDate : '2020-04-20',
            scheduleStartTime : '18:00',
            scheduleEndTime : '20:00',
            bookingCreatedAt : '2020-03-20'
        }
    }
    it('should render <BookingCard /> component', () => {
        const component = mount(<Provider store={store}><BookingCard {...props}/></Provider>);
        
        const footerText = component.find('#footer').map((node) => node.text());
        expect(footerText[0]).toMatch('Booked at 2020-03-20');
    });
})