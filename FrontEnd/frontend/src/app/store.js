import { configureStore } from '@reduxjs/toolkit';
import userSlice from './reducers/userSlice';
import modalSlice from './reducers/modalSlice';

export default configureStore({
  reducer : {
      user : userSlice,
      modal : modalSlice
  }
})