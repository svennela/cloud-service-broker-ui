export interface IServiceinstance {
  id?: number;
  instanceId?: string;
  name?: string;
  serviceId?: string;
  serviceName?: string;
  planId?: string;
  planName?: string;
  brokerName?: string;
  borkerId?: string;
  spaceGuid?: string;
  organizationGuid?: string;
  operationType?: string;
  operationId?: string;
}

export class Serviceinstance implements IServiceinstance {
  constructor(
    public id?: number,
    public instanceId?: string,
    public name?: string,
    public serviceId?: string,
    public planId?: string,
    public planName?: string,
    public brokerName?: string,
    public borkerId?: string,
    public serviceName?: string,
    public spaceGuid?: string,
    public organizationGuid?: string,
    public operationType?: string,
    public operationId?: string
  ) {}
}
