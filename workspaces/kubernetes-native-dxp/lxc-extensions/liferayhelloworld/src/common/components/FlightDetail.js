import React from 'react';

class FlightDetail extends React.Component {
  render() {
    return (
      <div>
        <hr />
        <h1>Flight Details</h1>
        <table width={"100%"}>
          <tr>
            <th>
              Airline
            </th>
            <th>
              Leg
            </th>
            <th>
              Time
            </th>
          </tr>
          {this.props.detail.legs.map(function (leg) {
            return (<tr>
              <td>
                {leg.airline}
              </td>
              <td>
                {leg.departureCode} &rarr; {leg.arrivalCode} 
              </td>
              <td>
                {leg.duration}
              </td>
            </tr>);
          })}
        </table>
      </div>
    );
  }
}

export default FlightDetail;