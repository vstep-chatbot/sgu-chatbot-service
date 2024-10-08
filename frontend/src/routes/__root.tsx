import * as React from "react";
import {Outlet, createRootRoute} from "@tanstack/react-router";
import {TanStackRouterDevtools} from "@tanstack/router-devtools";
import {ReactQueryDevtools} from "@tanstack/react-query-devtools";
import Sidebar from "@/components/sidebar/Sidebar";
import {Toaster} from "@/components/ui/toaster";

export const Route = createRootRoute({
  component: RootComponent,
});

function RootComponent() {
  return (
      <div className="dark:bg-background-900 flex h-full w-full bg-white">
          <Toaster/>
          <Sidebar/>
          <div className="h-full w-full dark:bg-zinc-950">
              <main
                  className={`mx-2.5 flex-none transition-all dark:bg-zinc-950 md:pr-2 xl:ml-[328px]`}
        >
                  <div className="mx-auto min-h-screen px-2 py-8">
                      <Outlet/>
                  </div>
              </main>
          </div>
      <TanStackRouterDevtools position="bottom-right" />
          <ReactQueryDevtools initialIsOpen={false} buttonPosition="bottom-left"/>
      </div>
  );
}
