import api from '../api';

const setJwtToken = token => {
    if(token) {
        api.defaults.headers.common['Authorization'] = token;
    } else {
        delete api.defaults.headers.common['Authorization'];
    }
}

export default setJwtToken;