"use client";

import "./index.css";

import { EyeOff, Pin, PinOff } from "lucide-react";
import * as React from "react";

import { Button } from "@/components/ui/button";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import {
  Column,
  ColumnDef,
  flexRender,
  getCoreRowModel,
  getFilteredRowModel,
  getPaginationRowModel,
  getSortedRowModel,
  Table,
  useReactTable,
} from "@tanstack/react-table";

import { Filter, SelectFilter } from "./Filter";
import TablePagination from "./Pagination";

type FilterOptions = { [key: string]: (string | boolean)[] };

type ReactTableProps<T> = {
  data: T[];
  columns: ColumnDef<T>[];
  filterOptions?: FilterOptions;
  globalSearch?: string;
};

function TableHeadPopover<T>({ column }: { column: Column<T> }) {
  const [_, setOpen] = React.useState(false);

  const togglePinColumn = () => {
    column.pin(column.getIsPinned() ? false : "left");
    setOpen(false);
  };

  return (
    <Popover>
      <PopoverTrigger asChild>
        <button className="absolute right-1 top-1 h-4 w-4">⋮</button>
      </PopoverTrigger>
      <PopoverContent align="end" className="w-fit p-0.5">
        <div className="flex flex-col items-start justify-center gap-1.5">
          <Button
            variant="ghost"
            className="min-w-[140px] justify-start"
            onClick={togglePinColumn}
          >
            {column.getIsPinned() ? (
              <div className="flex items-center gap-1.5">
                <PinOff size={20} className="inline" />
                Unpin column
              </div>
            ) : (
              <div className="flex items-center gap-1.5">
                <Pin size={20} className="inline" />
                Pin column
              </div>
            )}
          </Button>

          <Button
            variant="ghost"
            className="min-w-[140px] justify-start"
            onClick={() => column.toggleVisibility()}
          >
            <div className="flex items-center gap-1.5">
              <EyeOff size={20} className="inline" />
              Hide column
            </div>
          </Button>

          {column.getIsSorted() ? (
            <button
              className="underline"
              onClick={() => {
                column.clearSorting();
                setOpen(false);
              }}
            >
              Remove sorting
            </button>
          ) : null}
        </div>
      </PopoverContent>
    </Popover>
  );
}

type TableHeadProps<T> = {
  table: Table<T>;
  filterOptions?: FilterOptions;
};

function TableHead<T>({ table, filterOptions }: TableHeadProps<T>) {
  return (
    <thead>
      {table.getHeaderGroups().map((headerGroup) => (
        <tr key={headerGroup.id}>
          {headerGroup.headers.map((header) => {
            return (
              <th
                key={header.id}
                style={{
                  width: `calc(var(--header-${header.id}-size) * 1px)`,
                  height: "62px",
                }}
                className={
                  header.column.getIsPinned()
                    ? "border-Primary-50 border-2 shadow-sm"
                    : undefined
                }
              >
                <div className="flex h-full flex-col justify-start gap-1.5 p-3">
                  <div
                    onClick={header.column.getToggleSortingHandler()}
                    className={
                      (header.column.getCanSort() ? "cursor-pointer" : "") +
                      " self-start"
                    }
                    title={
                      header.column.getCanSort()
                        ? header.column.getNextSortingOrder() === "asc"
                          ? "Sort ascending"
                          : header.column.getNextSortingOrder() === "desc"
                            ? "Sort descending"
                            : "Clear sort"
                        : undefined
                    }
                  >
                    {header.isPlaceholder
                      ? null
                      : flexRender(
                          header.column.columnDef.header,
                          header.getContext(),
                        )}
                    {{
                      asc: " \u25B4",
                      desc: " \u25BE",
                    }[header.column.getIsSorted() as string] ?? null}
                  </div>
                  {header.column.getCanFilter() &&
                    (!!filterOptions?.[header.id] ? (
                      <div>
                        <SelectFilter
                          column={header.column}
                          options={filterOptions[header.id]}
                        />
                      </div>
                    ) : (
                      <div>
                        <Filter column={header.column} />
                      </div>
                    ))}

                  <TableHeadPopover column={header.column} />

                  <div
                    onDoubleClick={() => header.column.resetSize()}
                    onMouseDown={header.getResizeHandler()}
                    onTouchStart={header.getResizeHandler()}
                    className={`resizer${header.column.getIsResizing() ? "isResizing" : ""}`}
                  />
                </div>
              </th>
            );
          })}
        </tr>
      ))}
    </thead>
  );
}

function TableBody<T>({ table }: { table: Table<T> }) {
  return (
    <tbody>
      {table.getRowModel().rows.map((row) => (
        <tr key={row.id}>
          {row.getVisibleCells().map((cell) => (
            <td
              key={cell.id}
              style={{
                width: `calc(var(--col-${cell.column.id}-size) * 1px)`,
              }}
            >
              {flexRender(cell.column.columnDef.cell, cell.getContext())}
            </td>
          ))}
        </tr>
      ))}
    </tbody>
  );
}

export default function ReactTable<T extends object>({
  data,
  columns,
  filterOptions,
  globalSearch,
}: ReactTableProps<T>) {
  const table = useReactTable({
    data,
    columns,
    columnResizeMode: "onChange",
    state: {
      globalFilter: globalSearch,
    },
    getCoreRowModel: getCoreRowModel(),
    getFilteredRowModel: getFilteredRowModel(),
    getSortedRowModel: getSortedRowModel(),
    getPaginationRowModel: getPaginationRowModel(),
  });

  const pageSize = table.getState().pagination.pageSize;
  const pageOffset = table.getState().pagination.pageIndex;

  const columnSizingInfo = table.getState().columnSizingInfo;
  const columnSizeVars = React.useMemo(() => {
    const headers = table.getFlatHeaders();
    const colSizes: { [key: string]: number } = {};

    for (let i = 0; i < headers.length; i++) {
      if (!headers[i]) continue;

      const header = headers[i];
      colSizes[`--header-${header.id}-size`] = header.getSize();
      colSizes[`--col-${header.column.id}-size`] = header.column.getSize();
    }
    return colSizes;
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [columnSizingInfo, table]); // columnSizingInfo cần thiết nên không thể bỏ

  return (
    <div className="react-table-container flex max-w-full flex-col items-start overflow-x-auto">
      {table.getIsAllColumnsVisible() ? null : (
        <Button
          className="mb-2 ms-4"
          onClick={() => table.toggleAllColumnsVisible()}
        >
          Show all columns
        </Button>
      )}
      <table style={columnSizeVars} className="react-table w-full">
        <TableHead table={table} filterOptions={filterOptions} />
        <TableBody table={table} />
      </table>
      <TablePagination
        pageCount={table.getPageCount()}
        pageOffset={pageOffset}
        pageSize={pageSize}
        setPageSize={table.setPageSize}
        setPageIndex={table.setPageIndex}
        canPreviousPage={table.getCanPreviousPage()}
        canNextPage={table.getCanNextPage()}
      />
    </div>
  );
}
