import { createSlice } from '@reduxjs/toolkit';

export const slice = createSlice({
    name: 'user',
    initialState: {
      loggedIn : false,
      userDetails : null
    },
    reducers: {
        logIn : (state, userDetails) => {
            state.loggedIn = true;
            console.log(userDetails)
            state.userDetails = userDetails.payload;
        },
        logOut : (state) => {
            state.userDetails = null;
            state.loggedIn = false;
        }
    },
  });
  
  export const { logIn, logOut } = slice.actions;
  
  export default slice.reducer;