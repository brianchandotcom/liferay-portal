import React from 'react';
import FlightDetail from './FlightDetail';

class FlightSearch extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      from: "DFW",
      to: "BER",
      departureDate: "2022-06-01",
      adults: 2,
      detail: null
    };

    this.handleInputChange = this.handleInputChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.webClient = props.webClient;
  }

  handleInputChange(event) {
    const target = event.target;
    const value = target.type === 'checkbox' ? target.checked : target.value;
    const name = target.name;

    this.setState({
      [name]: value
    });
  }

  handleSubmit(event) {
    this.webClient.fetch(
      'flight/search?' + new URLSearchParams({
        'origin': this.state.from,
        'destination': this.state.to,
        'departureDate': this.state.departureDate,
        'adults': this.state.adults,
      })
    ).then((res) => {
      this.setState({"detail":res[0]});
    });
    event.preventDefault();
  }

  render() {
    return (
      <form onSubmit={this.handleSubmit}>
        <h1>Flight Search</h1>
        <table>
          <tr>
            <td>
              <label>
                From: 
                
              </label>
            </td>
            <td>
              <input
                name="from"
                type="text"
                defaultValue="DFW"
                onChange={this.handleInputChange} />
            </td>
          </tr>
          <tr>
            <td>
              <label>To:</label>
            </td>
            <td>
              <input
                name="to"
                type="text"
                defaultValue="BER"
                onChange={this.handleInputChange} />
            </td>
          </tr>
          <tr>
            <td>
              <label>Depart on:</label>
            </td>
            <td>
              <input
                name="departureDate"
                type="date"
                defaultValue="2022-06-01"
                onChange={this.handleInputChange} />
            </td>
          </tr>
          <tr>
            <td>
              <label># of adults:</label>
            </td>
            <td>
              <input
                name="adults"
                type="number"
                defaultValue="2"
                onChange={this.handleInputChange} />
            </td>
          </tr>
          <tr>
            <td colSpan="2"><input type="submit" value="Search" /></td>
          </tr>
        </table>
        {this.state.detail != null && <FlightDetail detail={this.state.detail} />}
      </form>
    );
  }
}

export default FlightSearch;