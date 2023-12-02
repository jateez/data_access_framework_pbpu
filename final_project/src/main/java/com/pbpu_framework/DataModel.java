// package com.pbpu_framework;

// import java.util.ArrayList;
// import java.util.List;

// public class DataModel {
//     private List<Data> data;

//     public DataModel() {
//         this.data = new ArrayList<>();
//     }

//     public List<Data> getData() {
//         return data;
//     }

//     public void createData(Data newData) {
//         data.add(newData);
//     }

//     public Data readData(int index) {
//         if (isValidIndex(index)) {
//             return data.get(index);
//         } else {
//             return null; // Or throw an exception based on your error-handling strategy
//         }
//     }

//     public void updateData(int index, Data updatedData) {
//         if (isValidIndex(index)) {
//             data.set(index, updatedData);
//         } else {
//             // Handle invalid index
//         }
//     }

//     public void deleteData(int index) {
//         if (isValidIndex(index)) {
//             data.remove(index);
//         } else {
//             // Handle invalid index
//         }
//     }

//     private boolean isValidIndex(int index) {
//         return index >= 0 && index < data.size();
//     }
// }
