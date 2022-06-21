import './App.css';
import {RegistrationModal} from "./authorization/RegistrationModal";
import {LoginModal} from "./authorization/LoginModal";
import {OrderPage} from "./order/OrderPage";

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <RegistrationModal></RegistrationModal>
          <LoginModal></LoginModal>
          <OrderPage></OrderPage>
      </header>
    </div>
  );
}

export default App;
