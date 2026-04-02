// (1) Find all the states that have a city called "BOSTON"
db.zips.aggregate([
  { $match: { city: "BOSTON" } },
  { $group: { _id: "$state" } }
]);

// (2) Find all the states and cities whose names include the string "BOST"
db.zips.aggregate([
  { $match: { city: { $regex: "BOST", $options: "i" } } },
  { $group: { _id: { state: "$state", city: "$city" } } }
]);

// (3) Find the city in each state with the most number of zip codes 
// and rank those cities along with the states using the city populations.
db.zips.aggregate([
  // Group by state and city to find zip count and total population per city
  { 
    $group: { 
      _id: { state: "$state", city: "$city" }, 
      zipCount: { $sum: 1 }, 
      cityPop: { $sum: "$pop" } 
    } 
  },
  // Sort by state, then by zipCount descending
  { $sort: { "_id.state": 1, zipCount: -1 } },
  // Group by state to get the city with the most zip codes
  { 
    $group: { 
      _id: "$_id.state", 
      city: { $first: "$_id.city" }, 
      maxZipCodes: { $first: "$zipCount" }, 
      cityPop: { $first: "$cityPop" } 
    } 
  },
  // Rank (sort) by city populations descending
  { $sort: { cityPop: -1 } }
]);
