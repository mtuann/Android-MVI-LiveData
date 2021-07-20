## MVI architecture patterns, MVI + LiveData + ViewModel
- This project is inherited by [MVI template Rohit Surwave](https://github.com/RohitSurwase/AAC-MVI-Architecture) with [Unidirectional by UET-AILab](https://gist.github.com/tqlong/8320e37904887763a1fec00a8673bd3a)
## For more explain you can [visit](https://medium.com/@rohitss/best-architecture-for-android-mvi-livedata-viewmodel-71a3a5ac7ee3)
- The process of intent will be following:
1. Create an action/ event (intent like touch a button)
    - Action will be process by Single Live Event, process each event each time
2. Process an action (this will change the model, data in app). There are 2 types of data:
    - Data fire-and-forget (for example - Toast)
    - Data that will save for other purpose
3. Render view by listening there will be change in the data (LiveData). There are also 2 types of render:
    - Render Effect (just like Toast, Snackbar,.. for data fire-and-forget)
    - Render State (corresponding to other data)