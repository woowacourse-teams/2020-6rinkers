import React from "react";
import "./index.css";
import AdminHeader from "./component/AdminHeader";
import Admin from "./component/Admin";
import {Route} from "react-router-dom";
import Main from "./home/Main";
import Footer from "./home/Footer";
import MainHeader from "./home/MainHeader";

const App = () => {
    return (
        <div>
            <Route exact path="/">
                <MainHeader/>
                <Main/>
            </Route>
            <Route path="/admin">
                <AdminHeader/>
                <Admin/>
            </Route>
            <Footer/>
        </div>
    );
};

export default App;
