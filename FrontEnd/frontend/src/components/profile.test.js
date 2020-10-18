import React from 'react';
import Profile from './Profile';
import { mount } from 'enzyme';
import { HashRouter as Router } from 'react-router-dom';
import { Provider } from 'react-redux';
import store from '../app/store';
import { setUser } from '../app/reducers/userSlice';
import Enzyme from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';

Enzyme.configure({ adapter: new Adapter() });
describe('<Profile /> component Unit Test', () => {
    const props = {
        router: {
            computedMatch: {
                params : {
                    id: null
                }
            }
        }
    }
    var item = {
        fName : 'Chinguun',
        lName : 'Undrakh',
        username : 'MyUsername',
        id : 1,
        email : 'chinguun9903@gmail.com',
        createdAt : '2020-03-20',
        address: '6090 Lonsdale street',
        pNumber: '0423131313'
    }

    
    store.dispatch(setUser(item))
    it('should render <Profile /> component', () => {
        const component = mount(<Provider store={store}>
                                    <Router>
                                        <Profile {...props}/>
                                    </Router>
                                </Provider>);
        
        const fName = component.find('#firstName').map((node) => node.text())[0];
        expect(fName).toMatch('First NameChinguun');
        const username = component.find('#username').map((node) => node.text())[0];
        expect(username).toMatch('UsernameMyUsername');
    });
})