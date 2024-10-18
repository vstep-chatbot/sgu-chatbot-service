import { FileRouteTypes } from "@/routeTree.gen";

export interface IRoute {
  path: FileRouteTypes["fullPaths"];
  name: string;
  disabled?: boolean;
  icon?: JSX.Element;
  secondary?: boolean;
  collapse?: boolean;
  items?: IRoute[];
  rightElement?: boolean;
  invisible?: boolean;
}

export type GetRecordsDto = {
  records: {
    _id: string;
    data: Record<string, string>;
  }[];
  count: number;
  competition_date: string;
  _id: string;
};
