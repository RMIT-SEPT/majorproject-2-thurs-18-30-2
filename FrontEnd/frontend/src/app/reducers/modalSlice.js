import { createSlice } from '@reduxjs/toolkit';

export const slice = createSlice({
    name: 'modal',
    initialState: {
      show : false,
      structure : {
          title : 'Title',
          body : 'Body'
      }
    },
    reducers: {
        openModal : (state, data) => {
            state.structure = data.payload;
            state.show = true;
        },
        exitModal : (state) => {
            state.show = false;
        }
    },
});
  
export const { exitModal, openModal } = slice.actions;
  
export default slice.reducer;