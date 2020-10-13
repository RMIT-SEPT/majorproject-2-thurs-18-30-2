import { createSlice } from '@reduxjs/toolkit';
import setJWTToken from '../securityUtils/setJWTToken';
import jwt_decode from 'jwt-decode';

export const slice = createSlice({
    name: 'user',
    initialState: {
      userDecoded: null,
      userDetais: null
    },
    reducers: {
        logIn : (state, loginInfo) => {
            var response = loginInfo.payload;
            localStorage.setItem('jwtToken', response.token);
            setJWTToken(response.token);
            const decoded = jwt_decode(response.token);
            state.userDecoded = { ...decoded };            
        },
        logOut : (state) => {
            state.userDetails = null;
            state.userDecoded = null;
            localStorage.setItem('jwtToken', null);
            setJWTToken(null);
        },
        setUser : (state, userDetails) => {
            state.userDetails = { ...userDetails.payload };
        },
        setDecoded : (state, token) => {
            var tok = token.payload;
            if(tok !== null) {
                setJWTToken(tok);
                const decoded = jwt_decode(tok);
                state.userDecoded = { ...decoded };
            }
        }
    },
  });
  
  export const { logIn, logOut, setUser, setDecoded } = slice.actions;
  
  export default slice.reducer;