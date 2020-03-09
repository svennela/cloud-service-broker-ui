export interface ICloudServiceBrokerUI {
  id?: number;
  brokerName?: string;
  brokerUrl?: string;
  userName?: string;
  password?: string;
  env?: string;
}

export class CloudServiceBrokerUI implements ICloudServiceBrokerUI {
  constructor(
    public id?: number,
    public brokerName?: string,
    public brokerUrl?: string,
    public userName?: string,
    public password?: string,
    public env?: string
  ) {}
}
